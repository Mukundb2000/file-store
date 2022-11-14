import '../../App.css';
import styled from "styled-components";
import {Routes, Route, useNavigate} from 'react-router-dom';
import Marquee from "react-fast-marquee";
import "./style.css";
function ServicesHome() {
//   const navigateservicespage = () => {
//     navigate('/');
//   };
  const Button = styled.button`
  background-color: #ffbb1c;
  color: '#ffbb1c';
  font-size: 20px;
  padding: 10px 40px;
  border-radius: 5px;
  margin: 10px 0px;
  cursor: pointer;
`;
const navigate = useNavigate();
const navigatedeletefile = () => {
  navigate('/deletefile');
};
const navigateleastmostfreqwords = () => {
    navigate('/leastmostfreqwords');
  };
  const navigatelistfiles = () => {
    navigate('/listfiles');
  };
  const navigateupdatefile = () => {
    navigate('/updatefile');
  };
  const navigateuploadnewfile = () => {
    navigate('/uploadnewfile');
  };
  const navigatewordcount = () => {
    navigate('/wordcount');
  };
  return (
    <div className="servicesmain">
        <h2>SERVICES AVAILABLE</h2>
        <Marquee speed={60} pauseOnHover={true}>
  Upload new file, List your files in store, Delete a file, Update a file, Current word count in store, Least or most frequent words
</Marquee>
    <div className='buttons'>
    <Button onClick={navigateuploadnewfile} >
          Upload new file
        </Button>
        <Button onClick={navigatelistfiles}>
          List files in store
        </Button>
        <Button onClick={navigatedeletefile}>
          Delete file
        </Button>
        <Button onClick={navigateupdatefile}>
          Update file
        </Button>
        <Button onClick={navigatewordcount}>
          Word Count
        </Button>
        <Button onClick={navigateleastmostfreqwords}>
          Least/most frequent words
        </Button>
    </div>
    </div>
  );
}

export default ServicesHome;
