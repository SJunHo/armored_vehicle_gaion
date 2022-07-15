import axios from "axios";
import authHeader from "../login/auth-header";

const API_URL = "http://localhost:8080";

class DataCollectionService{
    upload(file, onUploadProgress) {
        let formData = new FormData();
        formData.append("file", file);
        return axios.post( API_URL + "/upload",formData,{ headers:authHeader(), "Content-type": "multipart/form-data",onUploadProgress });
      }
}

export default new DataCollectionService();