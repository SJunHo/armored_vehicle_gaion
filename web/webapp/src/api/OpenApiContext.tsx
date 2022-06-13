import React, { useContext, useEffect, useState } from "react";
import { DatasetControllerApi, MlControllerApi } from "./gen";
import { MeContext } from "./MeContext";

type OpenApiContextProps = {
  mlControllerApi?: MlControllerApi;
  datasetControllerApi?: DatasetControllerApi;
};

export const basePath =
  process.env.REACT_APP_BASE_SERVER_URL !== "/"
    ? `http://${process.env.REACT_APP_BASE_SERVER_URL}`
    : `${window.location.protocol}//${window.location.hostname}:${window.location.port}`;

export const OpenApiContext = React.createContext<OpenApiContextProps>({});

export const OpenApiContextProvider: React.FC = ({ children }) => {
  const meData = useContext(MeContext);
  const apiKey = meData.token;

  const [value, setValue] = useState<OpenApiContextProps>({});

  useEffect(() => {
    const baseOptions = {
      headers: {
        Authorization: `Bearer ${apiKey}`,
      },
      isJsonMime: true,
    };
    setValue({
      mlControllerApi: new MlControllerApi({
        baseOptions,
        basePath,
        isJsonMime: () => true,
      }),
      datasetControllerApi: new DatasetControllerApi({
        baseOptions,
        basePath,
        isJsonMime: () => true,
      }),
    });
  }, [apiKey]);

  return (
    <OpenApiContext.Provider value={value}>{children}</OpenApiContext.Provider>
  );
};
