import axios from "axios";

const API_URL = "http://localhost:8082/api/auth/";

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

  register(id, username, email, password, usrth, phonenum, mltrank, mltnum, mltunit) {
    return axios.post(API_URL + "signup", {
      id,
      username,
      email,
      password,
      usrth,
      phonenum,
      mltrank,
      mltnum,
      mltunit
    });
  }
}

export default new AuthService();
