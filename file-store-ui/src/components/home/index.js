import '../../App.css';
import styled from "styled-components";
import {Routes, Route, useNavigate, BrowserRouter} from 'react-router-dom';

function Home() {
  const navigate = useNavigate();
  const navigateservicespage = () => {
    navigate('/serviceshome');
  };
  const Button = styled.button`
  background-color: #1c8aff;
  color: white;
  font-size: 20px;
  padding: 10px 60px;
  border-radius: 5px;
  margin: 10px 0px;
  cursor: pointer;
`;
  return (
    <div className="App">
      <header className="App-header">
        <p>
           <code>WELCOME TO FILESYNC</code>
           <p>One stop file-storage and management platform</p>
        </p>
        <Button onClick={navigateservicespage}>
          View Services
        </Button>
      </header>
    </div>
  );
}

export default Home;
