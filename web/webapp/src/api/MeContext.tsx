import React, { useEffect, useState } from "react";
import { decode } from "jsonwebtoken";
import { AuthProvider } from "./Auth";

type User = {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  address: string;
  zip: string;
  phone: string;
  role: string;
};

type MeContextProps = {
  user?: Partial<User>;
  token?: string;
};

export const MeContext = React.createContext<MeContextProps>({});

export const MeContextProvider: React.FC = ({ children }) => {
  const [user, setUser] = useState<Partial<User>>({});

  useEffect(() => {
    const token = AuthProvider.getToken();
    const decoded = decode(token) as User;
    setUser(decoded);
  }, []);

  return (
    <MeContext.Provider
      value={{
        user,
        token: AuthProvider.getToken(),
      }}
    >
      {children}
    </MeContext.Provider>
  );
};
