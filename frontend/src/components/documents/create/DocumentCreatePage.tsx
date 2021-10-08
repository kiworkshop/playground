import React from "react";
import {
  Box,
  Button,
  Card,
  CardContent,
  Divider,
  FormControl,
  Grid,
  InputLabel,
  makeStyles,
  MenuItem,
  Select,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow,
  TextField
} from "@material-ui/core";
import commonStyles from '../../../common/styles/CommonStyles'
import ApproverSelectModal from "./modal/ApproverSelectModal";

export interface IDocumentCategorySelectItem {
  value: string
  text: string
}

export interface IDocumentPageParams {
  category: string
  title: string
  contents: string
}

export interface IApprover {
  id: number
  jobPosition: string
  jobPositionText: string
  teamName: string
  name: string
}

interface IDocumentPage {
  params: IDocumentPageParams
  setParams: (params: IDocumentPageParams) => void
  approvers: IApprover[],
  setApprovers: (approvers: IApprover[]) => void
  categorySelectItems: IDocumentCategorySelectItem[]
  onConfirm: () => void
  approverSelectModalOpen: boolean
  setApproverSelectModalOpen: (open: boolean) => void
}

const DocumentCreatePage = (
  {
    params, setParams,
    approvers, setApprovers,
    categorySelectItems,
    onConfirm,
    approverSelectModalOpen, setApproverSelectModalOpen
  }: IDocumentPage) => {

  const commonStyleClasses = commonStyles()
  const classes = useStyles();

  return (
    <>
      <Grid container spacing={1}>

        <Grid item xs={12}>
          <Card elevation={0} className={commonStyleClasses.titleCard}>
            <Box p={2}>
              <div className={commonStyleClasses.title}>
                신규 문서 생성
              </div>
              <ul>
                <li>새로운 결재 문서를 기안할 수 있습니다.</li>
                <li>문서는 제목과 분류, 내용을 필수로 입력해야 합니다.</li>
                <li>결재선은 한 명 이상 지정해야 합니다.</li>
                <li>결재선이 본인 1명일 경우, 자동 상신됩니다.</li>
              </ul>
            </Box>
          </Card>
        </Grid>

        <Divider/>

        <Grid container justifyContent="flex-end">
          <Card elevation={0} className={classes.approverCardRoot} onClick={() => {
            setApproverSelectModalOpen(true)
          }}>
            <CardContent className={classes.approverCardContents}>
              <TableContainer>
                <Table className={classes.approverTable}>
                  <TableBody>
                    <TableRow>
                      {
                        approvers.length
                          ? (
                            approvers.map((approver, index) => {
                              return (
                                <TableCell className={classes.approverTableIndexCell} key={index}>
                                  {index + 1}
                                </TableCell>
                              )
                            })
                          )
                          : (
                            <TableCell className={classes.approverTableDefaultIndexCell}/>
                          )
                      }
                    </TableRow>
                    <TableRow>
                      {
                        approvers.length
                          ? (
                            approvers.map((approver, index) => {
                              return (
                                <TableCell className={classes.approverTableTeamCell} key={index}>
                                  {approver.teamName}
                                </TableCell>
                              )
                            })
                          )
                          : (
                            <TableCell className={classes.approverTableDefaultTeamCell}/>
                          )
                      }
                    </TableRow>
                    <TableRow>
                      {
                        approvers.length
                          ? (
                            approvers.map((approver, index) => {
                              return (
                                <TableCell className={classes.approverTableUserNameCell} key={index}>
                                  {approver.name}
                                </TableCell>
                              )
                            })
                          )
                          : (
                            <TableCell className={classes.approverTableUserNameCell}>
                              결재자 지정
                            </TableCell>
                          )
                      }
                    </TableRow>
                  </TableBody>
                </Table>
              </TableContainer>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12}>
          <Card elevation={0} className={classes.documentCardRoot}>
            <CardContent className={classes.documentCardContents}>

              <TableContainer>
                <Table className={classes.documentTable}>
                  <TableBody>
                    <TableRow>
                      <TableCell className={classes.documentCategoryTableCell}>
                        <FormControl variant="outlined" fullWidth className={classes.documentCategory}>
                          <InputLabel id="document-category">문서 분류</InputLabel>
                          <Select
                            labelId="document-category"
                            value={params.category}
                            onChange={event => setParams({...params, category: event.target.value as string})}
                            label="문서 분류"
                          >
                            {
                              categorySelectItems.map((selectItem, index) => {
                                return <MenuItem key={index} value={selectItem.value}>{selectItem.text}</MenuItem>
                              })
                            }
                          </Select>
                        </FormControl>
                      </TableCell>
                      <TableCell className={classes.documentTableCell}>
                        <TextField
                          value={params.title}
                          onChange={event => setParams({...params, title: event.target.value})}
                          required
                          fullWidth
                          label="문서 제목"
                          variant="outlined"
                        />
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell className={classes.documentTableCell} colSpan={2}>
                        <TextField
                          value={params.contents}
                          onChange={event => setParams({...params, contents: event.target.value})}
                          required
                          fullWidth
                          multiline
                          label="본문"
                          rows={15}
                          variant={"outlined"}
                        />
                      </TableCell>
                    </TableRow>
                  </TableBody>
                </Table>
              </TableContainer>

            </CardContent>
          </Card>

          <div className={classes.confirmButtonArea}>
            <Button
              variant="contained"
              color="primary"
              onClick={onConfirm}
            >
              문서 생성
            </Button>
          </div>
        </Grid>

      </Grid>

      <ApproverSelectModal
        approvers={approvers}
        setApprovers={setApprovers}
        open={approverSelectModalOpen}
        setOpen={setApproverSelectModalOpen}
      />
    </>
  );
}

const useStyles = makeStyles(theme => ({
  approverCardRoot: {
    backgroundColor: 'rgba(242,239,189,0.4)',
    margin: '0 5px 5px 0',
    cursor: 'pointer',
    '&:hover': {
      backgroundColor: 'rgba(242,239,189,0.6)',
    }
  },
  approverCardContents: {
    padding: '8px',
    '&:last-child': {
      paddingBottom: '8px'
    }
  },
  approverTable: {},
  approverTableDefaultIndexCell: {
    minWidth: 100,
    textAlign: 'center',
    border: '1px solid rgba(0, 0, 0, 0.3)',
    padding: '5px',
  },
  approverTableIndexCell: {
    minWidth: 100,
    textAlign: 'center',
    border: '1px solid rgba(0, 0, 0, 0.3)',
    padding: '0px',
  },
  approverTableDefaultTeamCell: {
    minWidth: 100,
    textAlign: 'center',
    border: '1px solid rgba(0, 0, 0, 0.3)',
    padding: '10px',
  },
  approverTableTeamCell: {
    minWidth: 100,
    textAlign: 'center',
    border: '1px solid rgba(0, 0, 0, 0.3)',
    padding: '3px',
  },
  approverTableUserNameCell: {
    minWidth: 100,
    height: 80,
    textAlign: 'center',
    border: '1px solid rgba(0, 0, 0, 0.3)',
    padding: '10px',
  },
  approverSearchArea: {
    textAlign: 'right',
    padding: '10px',
  },
  approverSearchButton: {
    color: 'white',
    backgroundColor: '#049DD9'
  },
  documentCardRoot: {
    minWidth: 275,
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
  documentTableCell: {
    textAlign: 'center',
    border: '1px solid rgba(68, 157, 222, 0.3)',
    padding: '10px',
  },
  documentCategoryTableCell: {
    textAlign: 'center',
    border: '1px solid rgba(68, 157, 222, 0.3)',
    padding: '10px',
    width: '200px',
  },
  documentCategory: {
    minWidth: '120px',
  },
  confirmButtonArea: {
    textAlign: 'right',
    padding: '10px',
  }
}));


export default DocumentCreatePage;
