import React from 'react';
import styles from './style.module.css';

type SectionProps = React.HTMLAttributes<HTMLDivElement> & {
  title: string;
  bottomTitle?: string;
};

export const Section: React.FC<SectionProps> = ({
  children,
  title,
  className,
  bottomTitle,
  ...props
}) => {
  return (
    <div className={`${styles.wrapper} ${className || ''}`} {...props}>
      <div className={styles.title}>{title}</div>
      <div className={styles.childrenWrapper}>
        <div className={styles.childrenContent}>{children}</div>
        {bottomTitle !== undefined ? (
          <div
            className={
              bottomTitle
                ? `${styles.bottomTitle} ${styles.hasText}`
                : styles.bottomTitle
            }>
            {bottomTitle}
          </div>
        ) : null}
      </div>
    </div>
  );
};

export const SubSection: React.FC<SectionProps> = ({ children, title }) => {
  return (
    <div className={styles.wrapper}>
      <div className={`${styles.title} ${styles.subSectionTitle}`}>{title}</div>
      <div
        className={`${styles.childrenWrapper} ${styles.subSectionChildrenWrapper}`}>
        {children}
      </div>
    </div>
  );
};
