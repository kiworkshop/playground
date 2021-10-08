import React from 'react';
import {makeStyles} from "@material-ui/core";

const MainPage = () => {

  const classes = useStyles();

  return (
    <>
      <h2 className={classes.mainTitle}>환영합니다!</h2>
    </>
  );
}

const useStyles = makeStyles((theme) => ({
  mainTitle: {
    textAlign: 'center',
  }
}));

export default MainPage;
