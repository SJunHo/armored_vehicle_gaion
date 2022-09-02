import React, {useContext, useState} from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import {useTranslation} from 'react-i18next';
import {
  OpenApiContext, UpdateDataLookupInput
} from '../../api';


export const DataLookUpNew: React.FC<{ algorithName: string }> = () => {
  const { t } = useTranslation();

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [inputState, setinputState] = useState<UpdateDataLookupInput>({
    indexOfLabeledField: 1,
  });

  const { datasetControllerApi } = useContext(OpenApiContext);

  return (
    <>
      <Button variant='primary' onClick={handleShow}>
        {t('pp.btn.add')}
      </Button>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>{t('pp.header.lookup')}</Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <Form>
            <Form.Group className='mb-3'>
              <Form.Label>{t('pp.popup.lookupName')}</Form.Label>
              <Form.Control
                type='text'
                value={inputState.lookupName}
                onChange={(v) =>
                  setinputState((old) => ({
                    ...old,
                    lookupName: v.target.value,
                  }))
                }
              />
            </Form.Group>
            <Form.Group className='mb-3'>
              <Form.Label>{t('pp.es.index')}</Form.Label>
              <Form.Control
                type='text'
                value={inputState.index}
                onChange={(v) =>
                  setinputState((old) => ({ ...old, index: v.target.value }))
                }
              />
            </Form.Group>

            <Form.Group className='mb-3'>
              <Form.Label>{t('pp.dsm.deli')}</Form.Label>
              <Form.Control
                type='text'
                value={inputState.delimiter}
                onChange={(v) =>
                  setinputState((old) => ({
                    ...old,
                    delimiter: v.target.value,
                  }))
                }
              />
            </Form.Group>
            <Form.Group className='mb-3'>
              <Form.Label>{t('pp.dsm.ilc')}</Form.Label>
              <Form.Control
                type='text'
                value={inputState.indexOfLabeledField}
                onChange={(v) =>
                  setinputState((old) => ({
                    ...old,
                    indexOfLabeledField: parseInt(v.target.value),
                  }))
                }
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant='primary'
            type='submit'
            className='button btn-block col-12'
            onClick={() => {
              datasetControllerApi?.createDataLookup(inputState);
              setShow(false)
            }}>
            {t('pp.popup.btn.submit')}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
