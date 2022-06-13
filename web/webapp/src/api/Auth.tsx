import axios from "axios";
import { baseURL } from "./api";

export type AuthData = {
  token: string;
  refreshToken: string;
};

class Auth {
  private readonly endpoint: string;

  constructor(endpoint: string) {
    this.endpoint = endpoint;
  }

  async signIn(username: string, password: string): Promise<AuthData> {
    const res = await axios.post<AuthData>(
      `${this.endpoint ? "http://" + this.endpoint : ""}/auth/login`,
      { username, password }
    );
    if (res.status !== 200) {
      throw new Error("Invalid username or password");
    }
    // eslint-disable-next-line no-undef
    localStorage.setItem("authToken", res.data.token);
    // eslint-disable-next-line no-undef
    localStorage.setItem("authRefreshToken", res.data.refreshToken);
    return res.data;
  }

  getToken(): string {
    // eslint-disable-next-line no-undef
    const res = localStorage.getItem("authToken");
    
    return res || ''
  }

  isAuth() {
    // eslint-disable-next-line no-undef
    return !!localStorage.getItem("authToken");
  }

  signOut() {
    // eslint-disable-next-line no-undef
    localStorage.removeItem("authToken");
    // eslint-disable-next-line no-undef
    localStorage.removeItem("authRefreshToken");
  }
}

export class UnauthorizedError extends Error {}

export const AuthProvider = new Auth(baseURL);
