import axios from "axios";
import { BASE_URL } from "../url";

const API_URL = BASE_URL + "/api/auth/";

class AuthService {
  login(id, password) {
    return axios
      .post(API_URL + "signin", { id, password })
      .then((response) => {
        if (response.data.accessToken) {
          console.log(response.data);
          localStorage.setItem("user", JSON.stringify(response.data));
          localStorage.setItem("authToken", JSON.stringify(response.data.accessToken));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
    localStorage.removeItem("authToken");
  }

  register(data) {
    return axios.post(API_URL + "signup", data , {"Content-type": "application/json", });
  }
}

export default new AuthService();
