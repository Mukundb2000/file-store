import fetch from "node-fetch";
const Delete = async (fileName) => {
  var res = await fetch("http://localhost:8081/files?fileName=" + fileName, {
    method: "GET",
  });
  var response = await res.json();
  if (response.length > 0) {
    var resDel = await fetch("http://localhost:8081/files/" + fileName, {
      method: "DELETE",
    });
    if(resDel.ok){
        console.log("File Deleted Successfully");
    }
    else{
        console.log("Failed to Delete File, Please try again later");
    }
  } else {
    console.log("No Such Files Exist in the Store");
  }
};
export default Delete;
