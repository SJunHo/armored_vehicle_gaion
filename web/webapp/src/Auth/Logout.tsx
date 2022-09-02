import React, { useEffect } from "react";
import { useHistory } from "react-router-dom";
import { AuthProvider } from "../api";

export const Logout: React.FC = () => {
  const history = useHistory();
  useEffect(() => {
    if (history) {
      AuthProvider.signOut();
      history.push("/auth/login");
    }
  }, [history]);
  return <div />;
};
