import React from "react";
import styles from "./styles.module.css";

export const Page: React.FC = ({ children }) => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.childrenWrapper}>{children}</div>
    </div>
  );
};
