import axios from "axios";
import authHeader from "../login/auth-header";

const API_URL = "http://localhost:8080";

class DataCollectionService{
    upload(file, onUploadProgress) {
        let formData = new FormData();
        console.log(onUploadProgress);
        formData.append("file", file);
        console.log(file);
        return axios.post( API_URL + "/upload",formData,{ headers:authHeader(), "Content-type": "multipart/form-data",onUploadProgress });
      }

      getFiles() {
        return axios.get( API_URL + "/files" ,{headers : authHeader()});
      }
}

export default new DataCollectionService();