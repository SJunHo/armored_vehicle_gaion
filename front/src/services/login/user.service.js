import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8082/api/user";

class UserService {
  get(id) {
    return axios.get( API_URL + `/info/${id}`, { headers: authHeader() , "Content-type": "application/json", });
  }
  getUserList(params) {
    return axios.get( API_URL + "/list", { headers: authHeader() , params });
  }

  update(data) {
    return axios.post( API_URL + `/update`, data , { headers: authHeader() , "Content-type": "application/json", });
  }
  
  delete(id) {
    return axios.get( API_URL + `/delete/${id}` , { headers: authHeader() , "Content-type": "application/json", });
  }
}

export default new UserService();
