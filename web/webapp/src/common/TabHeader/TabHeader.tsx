import React, {useEffect, useState} from 'react'
import styles from './style.module.css'

type TabHeaderType = {
  id: string
  title: string
}

type Props = {
  headers: TabHeaderType[]
  activeTabId?: string
  onChangeActiveTab?: (v: string) => void
}

export const TabHeader: React.FC<Props> = ({ headers, activeTabId, onChangeActiveTab }) => {
  const [aTabId, setATabId] = useState<string>()

  useEffect(() => {
    setATabId(activeTabId)
  }, [activeTabId])

  return (
    <header className={styles.wrapper}>
      {headers.map(header => (
        <div className={aTabId === header.id ? `${styles.headerItem} ${styles.active}` : styles.headerItem} key={header.id} onClick={() => {
          if (onChangeActiveTab) {
            onChangeActiveTab(header.id)
          } else {
            setATabId(header.id)
          }
        }}>{header.title}</div>
      ))}
      <div className={styles.rest} />
    </header>
  )
}