import { useState, useEffect } from 'react'
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import {useTranslation} from 'react-i18next';
import {DataLookup, UpdateDataLookupInput} from '../../api';

type Props = { visible: boolean, onChangeVisible: (v: boolean) => void, onUpdate: (v: UpdateDataLookupInput) => any, selectedData?: DataLookup }

export const DataLookUpEdit: React.FC<Props> = ({ visible, onChangeVisible, onUpdate, selectedData }) => {
  const { t } = useTranslation();

  const [inputState, setinputState] = useState<UpdateDataLookupInput>({
    indexOfLabeledField: 1,
  });

  useEffect(() => {
    if (selectedData !== undefined) {
      setinputState(selectedData)
    } else {
      setinputState({})
    }
  }, [selectedData])

  return (
    <>
      <Modal show={visible} onHide={() => onChangeVisible(false)}>
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
            onClick={() => onUpdate(inputState)}>
            {t('pp.popup.btn.submit')}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
