import Modal from "react-bootstrap/Modal";
import { useTranslation } from "react-i18next";
import Button from "react-bootstrap/Button";
import React from "react";
import { Alert } from "react-bootstrap";

export const DataLookUpDelete: React.FC<{
  onDelete: () => void;
  visible: boolean;
  onCancel: () => void;
}> = ({ onDelete, visible, onCancel }) => {
  const { t } = useTranslation();

  return (
    <Modal show={visible} onHide={onCancel}>
      <Modal.Header closeButton>
        <Modal.Title>Delete Dataset</Modal.Title>
      </Modal.Header>

      <Modal.Body>
        <Alert variant="danger">Do you want to delete the dataset?</Alert>
      </Modal.Body>

      <Modal.Footer>
        <Button variant="danger" onClick={onDelete}>
          {t("pp.btn.yes")}
        </Button>
        <Button variant="secondary" onClick={onCancel}>
          {t("pp.btn.no")}
        </Button>
      </Modal.Footer>
    </Modal>
  );
};
