import React, { useState } from "react";
import { useHistory } from "react-router";
import { AuthProvider } from "../api";
import "./login.css";

type LoginInput = {
  id: string;
  password: string;
};

export const Login: React.FC = () => {
  const [loginInput, setLoginInput] = useState<Partial<LoginInput>>({});
  const [resultText, setReseultText] = useState("");
  const history = useHistory();

  const handleLogin = async () => {
    try {
      const response = await AuthProvider.signIn(
        loginInput.id || "",
        loginInput.password || ""
      );
      if (response.token) {
        console.log(response);
        history.push("/");
      }
    } catch (e) {
      console.log(e);
      setReseultText("로그인 오류");
    }
  };

  return (
    <div className="login_page">
      <div className="login">
        <div className="login_screen">
          <div className="login_title">User Login</div>
          <div className="login_form">
            <div className="control_group">
              <input
                type="text"
                className="login_field"
                value={loginInput.id || ""}
                placeholder="ID"
                onChange={(e) => {
                  const id = e.target.value;
                  setLoginInput((old) => ({ ...old, id }));
                }}
              />
            </div>
            <div className="control_group">
              <input
                type="password"
                className="login_field"
                value={loginInput.password || ""}
                placeholder="Password"
                onChange={(e) => {
                  const password = e.target.value;
                  setLoginInput((old) => ({ ...old, password }));
                }}
              />
            </div>
            <div style={{ color: "red", fontSize: "15px" }}>{resultText}</div>
            <div>
              <button className="btn_s" onClick={() => handleLogin()}>
                Login
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
