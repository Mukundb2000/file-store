import "./App.css";
import styled from "styled-components";
import { Routes, Route, useNavigate, BrowserRouter } from "react-router-dom";
import ServicesHome from "./components/serviceshome";
import Home from "./components/home";
import DeleteFile from "./components/deletefile";
import LeastMostFreq from "./components/leastmostfreqwords";
import ListFiles from "./components/listfiles";
import UpdateFile from "./components/updatefile";
import UploadNewFile from "./components/uploadnewfile";
import WordCount from "./components/wordcount";

function App() {
  const Button = styled.button`
    background-color: "#";
    color: white;
    font-size: 20px;
    padding: 10px 60px;
    border-radius: 5px;
    margin: 10px 0px;
    cursor: pointer;
  `;
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/serviceshome" element={<ServicesHome />} />
          <Route path="/leastmostfreqwords" element={<LeastMostFreq />} />
          <Route path="/deletefile" element={<DeleteFile />} />
          <Route path="/listfiles" element={<ListFiles />} />
          <Route path="/updatefile" element={<UpdateFile />} />
          <Route path="/uploadnewfile" element={<UploadNewFile />} />
          <Route path="/wordcount" element={<WordCount />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
