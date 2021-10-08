import React, {useCallback, useEffect, useState} from "react";
import {
  Backdrop,
  Card,
  CardContent,
  Fade,
  makeStyles,
  Modal,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow
} from "@material-ui/core";
import {request} from "../../utils/requestUtils";
import {convertLocalDateTimeToString} from "../../utils/localDateTimeUtils";

interface IDocumentDrafter {
  id: number
  jobPositionText: string
  name: string
  teamName: string
}

interface IDocumentApprover {
  approvalOrder: number
  approvalState: string
  approvalStateText: string
  approverName: string
  approverTeamName: string
  approvalComment: string
}

interface IDocument {
  id: number
  createdDateTime: string
  title: string
  approvalStateText: string
  categoryText: string
  contents: string
  drafter: IDocumentDrafter
  approvers: IDocumentApprover[]
}

export interface IDocumentModalOpen {
  documentId: number
  open: boolean
}

interface IDocumentModal {
  modalOpen: IDocumentModalOpen
  setModalOpen: (modalOpen: IDocumentModalOpen) => void
}

const DocumentModal = (
  {
    modalOpen, setModalOpen
  }: IDocumentModal) => {

  const classes = useStyles();

  const initialDocumentState = {
    id: 0,
    createdDateTime: '',
    title: '',
    approvalStateText: '',
    categoryText: '',
    contents: '',
    drafter: {
      id: 0,
      jobPositionText: '',
      name: '',
      teamName: ''
    },
    approvers: []
  };
  const [document, setDocument] = useState<IDocument>(initialDocumentState);

  const fetchDocument = useCallback(async () => {
    const {data: document} = await request.get(`/api/documents/${modalOpen.documentId}`);

    setDocument(document);
  }, [modalOpen.documentId])

  const handleClose = () => {
    setModalOpen({documentId: 0, open: false});
    clear();
  };

  const clear = () => {
    setDocument(initialDocumentState);
  }

  useEffect(() => {
    if (modalOpen.open) {
      fetchDocument();
    }
  }, [modalOpen.open, fetchDocument]);

  return (
    <>
      <Modal
        className={classes.modal}
        open={modalOpen.open}
        onClose={handleClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={modalOpen.open}>
          <div className={classes.modalContents}>
            <div className={classes.modalTitle}>
              문서 상세
            </div>

            <Card elevation={0} className={classes.documentCardRoot}>
              <CardContent className={classes.documentCardContents}>

                <TableContainer>
                  <Table className={classes.documentTable}>
                    <TableBody>

                      <TableRow>
                        <TableCell width="13%" className={classes.documentTableCell}>
                          분류
                        </TableCell>
                        <TableCell width="24%" className={classes.documentTableResultCell}>
                          {document.categoryText}
                        </TableCell>
                        <TableCell width="13%" className={classes.documentTableCell}>
                          제목
                        </TableCell>
                        <TableCell className={classes.documentTableResultCell}>
                          {document.title}
                        </TableCell>
                      </TableRow>

                      <TableRow>
                        <TableCell className={classes.documentTableCell}>
                          결재상태
                        </TableCell>
                        <TableCell className={classes.documentTableResultCell}>
                          {document.approvalStateText}
                        </TableCell>
                        <TableCell className={classes.documentTableCell}>
                          기안자
                        </TableCell>
                        <TableCell className={classes.documentTableResultCell}>
                          {`${document.drafter.teamName} ${document.drafter.name}`}
                        </TableCell>
                      </TableRow>

                      <TableRow>
                        <TableCell className={classes.documentTableCell}>
                          생성시간
                        </TableCell>
                        <TableCell
                          className={classes.documentTableResultCell}
                          style={{textAlign: 'left', paddingLeft: '20px'}}
                          colSpan={3}
                        >
                          {convertLocalDateTimeToString(document.createdDateTime)}
                        </TableCell>
                      </TableRow>

                      <TableRow className={classes.documentTableContentsRow}>
                        <TableCell className={classes.documentTableCell}>
                          내용
                        </TableCell>
                        <TableCell className={classes.documentTableContentsCell} colSpan={3}>
                          {document.contents}
                        </TableCell>
                      </TableRow>

                      {
                        document.approvers.map((approver, index) => {
                          return (
                            <TableRow key={index}>
                              <TableCell className={classes.documentTableCell}>
                                결재 {approver.approvalOrder}
                              </TableCell>
                              <TableCell className={classes.documentTableResultCell}>
                                {`${approver.approverTeamName} ${approver.approverName}`}
                              </TableCell>
                              <TableCell className={classes.documentTableCell}>
                                {approver.approvalStateText}
                              </TableCell>
                              <TableCell className={classes.documentTableResultCell}>
                                {
                                  approver.approvalComment ? approver.approvalComment
                                    : approver.approvalState === 'APPROVED' || approver.approvalState === 'CANCELED' ? '(상신의견없음)'
                                    : ''
                                }
                              </TableCell>
                            </TableRow>
                          )
                        })
                      }

                    </TableBody>
                  </Table>
                </TableContainer>

              </CardContent>
            </Card>

          </div>
        </Fade>
      </Modal>
    </>
  );
}

const useStyles = makeStyles(theme => ({
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  modalContents: {
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: '20px 20px 10px 20px',
    borderRadius: '5px',
    outline: 'none',
    maxHeight: '1700px',
    overflow: 'scroll',
  },
  modalTitle: {
    fontFamily: 'BM_HANNA_11yrs_old',
    fontSize: '25px',
    padding: '0 0 15px 15px',
  },
  modalTitleDescription: {
    padding: '0 0 20px 15px',
  },
  documentCardRoot: {
    minWidth: 700,
    backgroundColor: 'rgba(66, 151, 212, 0.08)',
  },
  documentCardContents: {
    padding: '8px',
    '&:last-child': {
      paddingBottom: '8px'
    }
  },
  documentTable: {
    minWidth: 650,
  },
  documentTableContentsRow: {
    height: '600px',
  },
  documentTableContentsCell: {
    border: '1px solid rgba(68, 157, 222, 0.3)',
    padding: '20px',
    backgroundColor: '#F7F9FA',
    verticalAlign: 'top',
    whiteSpace: 'pre-line',
  },
  documentTableCell: {
    textAlign: 'center',
    border: '1px solid rgba(68, 157, 222, 0.3)',
    padding: '10px',
  },
  documentTableResultCell: {
    textAlign: 'center',
    border: '1px solid rgba(68, 157, 222, 0.3)',
    padding: '10px',
    backgroundColor: '#F7F9FA',
  }
}));

export default DocumentModal;
