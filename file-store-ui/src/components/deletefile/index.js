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
