import {makeStyles} from '@material-ui/core/styles';

const commonStyles = makeStyles({
  titleCard: {
    padding: '0 0 0 10px',
    backgroundColor: '#fafafa',
  },
  title: {
    fontFamily: 'BM_HANNA_11yrs_old',
    fontSize: '40px',
  },
  resultTable: {
    minWidth: 650,
  },
  resultTableHeadCell: {
    textAlign: 'center',
    borderRight: '1px solid rgba(224, 224, 224, 0.7)',
    backgroundColor: 'rgba(235, 235, 235, 0.5)',
  },
  resultTableBodyCell: {
    textAlign: 'center',
    borderRight: '1px solid rgba(224, 224, 224, 0.5)',
  },
});

export default commonStyles;
