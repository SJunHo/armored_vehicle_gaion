import axios from "axios";

const API_URL = "http://localhost:8082/api/auth/";

class AuthService {
  login(id, password) {
    return axios
      .post(API_URL + "signin", { id, password })
      .then((response) => {
        if (response.data.accessToken) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(id, username, email, password) {
    return axios.post(API_URL + "signup", {
      id,
      username,
      email,
      password,
    });
  }
}

export default new AuthService();
