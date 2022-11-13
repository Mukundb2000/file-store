import "../../App.css";
import styled from "styled-components";
import "./style.css";
import { Routes, Route, useNavigate, BrowserRouter } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
function UploadNewFile() {
  const Button = styled.button`
    font-size: 20px;
    padding: 10px 40px;
    border-radius: 5px;
    margin: 10px 0px;
    cursor: pointer;
  `;
  const notify = () =>  <input hidden id="fileUpload" type="file" accept="video/*" />;
  return (
    <div className="main">
      <h2>UPLOAD FILE HERE</h2>
      <div className="imagediv">
        <img
          src="http://www.clker.com/cliparts/4/1/1/8/13501544242063799778upload.svg.med.png"
          className="uploadbuttonimage"
          onClick={notify}
        />
      </div>
      <Button onClick={notify}>Upload File</Button>
      <div>
      <label htmlFor="fileUpload">
        <div>
          <h3>Open</h3>
          <p>Other stuff in here</p>
        </div>
      </label>
      <input hidden id="fileUpload" type="file" accept="video/*" />
    </div>
    </div>
  );
}

export default UploadNewFile;
