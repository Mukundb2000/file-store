import fetch from "node-fetch";
const List=async ()=>{
    function File(file_name, total_size, uploaded_size, created_at, status) {
        this.file_name = file_name;
        this.total_size = total_size;
        this.uploaded_size = uploaded_size;
        this.created_at = created_at;
        this.status = status;
      }
    var res = await fetch("http://localhost:8081/files", {
      method: "GET"
    });
    var response = await res.json();
    var files = [];
    if(response.length > 0){
    response.forEach((file)=>files.push(new File(file.name,file.totalFileSize,file.uploadedSize,file.createdAt,file.status)));
    console.table(files);
    }
    else{
        console.log("No Files Exist in the Store")
    }
}
export default List;