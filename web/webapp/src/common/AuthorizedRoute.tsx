import React, { useEffect } from "react";
import { useHistory, RouteProps, Route } from "react-router-dom";
import { AuthProvider } from "../api/Auth";

export const AuthorizedRoute: React.FC<RouteProps> = ({
  component,
  ...props
}) => {
  const history = useHistory();

  useEffect(() => {
    if (!AuthProvider.isAuth()) {
      console.error("Unexpected Error, Please login again");
      AuthProvider.signOut();
    }
  });

  useEffect(() => {
    if (!AuthProvider.isAuth()) {
      history.push("/auth/login");
    }
  }, [history]);

  return AuthProvider.isAuth() ? (
    <Route {...props} component={component} />
  ) : null;
};
