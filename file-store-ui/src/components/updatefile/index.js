import "../../App.css";
import styled from "styled-components";
import "./style.css";
import fetch from "node-fetch";
import * as fs from "../../../node_modules/fs";
import crypto from "../../../node_modules/crypto";
import { Routes, Route, useNavigate, BrowserRouter } from "react-router-dom";
function UpdateFile() {
  const Button = styled.button`
    background-color: #ffbb1c;
    color: "#ffbb1c";
    font-size: 20px;
    padding: 10px 40px;
    border-radius: 5px;
    margin: 10px 0px;
    cursor: pointer;
  `;
  const upload = async (fileName) => {
    const rl = readline.createInterface({
      input: process.stdin,
      output: process.stdout,
    });
    const chunkSize = 1000;
    console.log("Processing file " + fileName);
    var getHash = (content) => {
      var hash = crypto.createHash("md5");
      //passing the data to be hashed
      var data = hash.update(content, "utf-8");
      //Creating the hash in the required format
      var gen_hash = data.digest("hex");
      return gen_hash;
    };

    const stats = fs.statSync(fileName);
    const fileSizeInBytes = stats.size;
    console.log("file size is" + fileSizeInBytes);
    let hashCode = "";
    let readStream = fs.createReadStream(fileName);
    var rContents = ""; // to hold the read contents;
    readStream.on("data", function (chunk) {
      rContents += chunk;
    });
    readStream.on("error", function (err) {
      console.log(err);
    });
    readStream.on("end", async function () {
      //Calling the getHash() function to get the hash
      hashCode = getHash(rContents);
      await initiateUploading();
    });
    const uploadFile = async (startInd, override = false) => {
      if (startInd > fileSizeInBytes) {
        confirm("File Completly uploaded");
        return;
      }
      var upload = true;
      while (upload) {
        var readStream = fs.createReadStream(fileName, {
          start: startInd,
          encoding: "UTF-8",
          end:
            fileSizeInBytes - startInd > chunkSize
              ? startInd + chunkSize
              : fileSizeInBytes,
        });
        var resUp = await fetch("http://localhost:8081/files", {
          method: override ? "PUT" : "POST",
          headers: {
            "x-file-name": fileName,
            "x-file-size": fileSizeInBytes,
            "x-file-hash": hashCode,
            "x-start-byte": startInd,
          },
          body: readStream,
        });
        var responseUp = await resUp.json();
        if (
          responseUp.uploadedSize == responseUp.totalFileSize ||
          responseUp.status == "COMPLETE"
        ) {
          upload = false;
          confirm("File successfully uploaded");
        } else {
          startInd = responseUp.uploadedSize + 1;
        }
      }
    };
    const initiateUploading = async () => {
      console.log("Hash : " + hashCode);
      var res = await fetch(
        "http://localhost:8081/files?fileName=" + fileName,
        {
          method: "GET",
        }
      );
      const fileNameRes = await res.json();
      console.log(JSON.stringify(fileNameRes));
      var fileNameExist = false;
      if (fileNameRes.length > 0) {
        fileNameExist = true;
      }
      res = await fetch("http://localhost:8081/files?hash=" + hashCode, {
        method: "GET",
      });
      const hashCodeRes = await res.json();
      console.log(JSON.stringify(hashCodeRes));
      var fileContentExist = false;
      var fileStatus = "ND";
      var fileUploadedSize = 0;
      var uploadedFileName = "";
      if (hashCodeRes.length > 0) {
        fileContentExist = true;
        fileStatus = hashCodeRes[0].status;
        fileUploadedSize = hashCodeRes[0].uploadedSize;
        uploadedFileName = hashCodeRes[0].name;
      }

      switch (true) {
        case !fileNameExist && !fileContentExist:
          await uploadFile(0); // new file
          break;
        case fileNameExist && fileContentExist && fileStatus == "INCOMPLETE":
          await uploadFile(fileUploadedSize + 1); // CONTINUE UPDATING THE FILE
          break;
        case fileNameExist && !fileContentExist: // ask to override a file as filename is colliding
          var input = confirm(
            "File Name already exist, Do you want to override"
          );
          if (input) await uploadFile(0, true);
          break;
        case !fileNameExist && fileContentExist && fileStatus == "INCOMPLETE": // ask to override the name but continue the file uploading
          var input = confirm(
            "File Name already exist, Do you want to override"
          );
          if (input) await uploadFile(fileUploadedSize + 1, true);
          break;
        case !fileNameExist && fileContentExist && fileStatus == "COMPLETE": // file with this content already exist and provie with file name
        confirm(
            "File Content Already Exist with the file name as " +
              uploadedFileName
          );
          break;
        case fileNameExist && fileContentExist && fileStatus == "COMPLETE": // file already exist
        confirm("File Already Exist with same name and same content");
          break;
      }
    };
  };
  return (
    <div className="main">
        <p>Enter the filename which you want to delete:</p>
       <form>
        <label className="filenametext">
          Filename:
          <input type="text" name="Filename" />
        </label>
        <input type="submit" value="Submit" />
      </form>
    </div>
  );
}

export default UpdateFile;
