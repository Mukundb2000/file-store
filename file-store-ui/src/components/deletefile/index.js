import "../../App.css";
import styled from "styled-components";
import "./style.css";
import { Routes, Route, useNavigate, BrowserRouter } from "react-router-dom";
function DeleteFile() {
  const Button = styled.button`
    background-color: #ffbb1c;
    color: "#ffbb1c";
    font-size: 20px;
    padding: 10px 40px;
    border-radius: 5px;
    margin: 10px 0px;
    cursor: pointer;
  `;
  const deleteFile = async (fileName) => {
    var res = await fetch("http://localhost:8081/files?fileName=" + fileName, {
      method: "GET",
    });
    var response = await res.json();
    if (response.length > 0) {
      var resDel = await fetch("http://localhost:8081/files/" + fileName, {
        method: "DELETE",
      });
      if(resDel.ok){
          confirm("File Deleted Successfully");
      }
      else{
          confirm("Failed to Delete File, Please try again later");
      }
    } else {
      confirm("No Such Files Exist in the Store");
    }
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

export default DeleteFile;
