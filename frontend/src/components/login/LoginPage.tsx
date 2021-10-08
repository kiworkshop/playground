import React, {KeyboardEvent, useCallback, useEffect, useState} from 'react';
import {Avatar, Box, Button, Container, CssBaseline, makeStyles, TextField, Typography} from "@material-ui/core";
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Copyright from "../copyright/Copyright";
import {request} from "../../utils/requestUtils";
import {useHistory} from "react-router-dom";
import {useDispatch} from "react-redux";
import {resetUserLoginInfo, setUserLoginInfo} from 'reducers/UserLoginInfoReducer';

interface ILoginInfo {
  email: string
  password: string
}

const LoginPage = () => {

  const classes = useStyles();
  const history = useHistory();
  const dispatch = useDispatch();

  const [loginInfo, setLoginInfo] = useState<ILoginInfo>({
    email: '',
    password: ''
  });

  const checkLogin = useCallback(async () => {
    const token = localStorage.getItem("token");
    if (token != null) {
      try {
        const response = await request.get('/api/login/token');
        if (response.status === 200) {
          history.push("/main");
          return;
        }

      } catch (error) {
        localStorage.removeItem('token')
        dispatch(resetUserLoginInfo());
      }
    }

    dispatch(resetUserLoginInfo());
  }, [history])

  const login = async () => {
    const response = await request.post('/api/login', loginInfo);

    if (response.status === 200) {
      const token = response.data.token
      localStorage.setItem("token", token)

      dispatch(setUserLoginInfo({email: response.data.email, name: response.data.name}));

      history.push("/main");
      return;
    }
  }

  const enterLogin = (event: KeyboardEvent) => {
    if (event.key === 'Enter') {
      login()
    }
  }

  useEffect(() => {
    checkLogin();
  }, [checkLogin])

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline/>
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <LockOutlinedIcon/>
        </Avatar>
        <Typography component="h1" variant="h5">
          WBD 인사시스템
        </Typography>
        <div className={classes.form}>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="email"
            label="이메일"
            name="email"
            autoComplete="email"
            autoFocus
            onChange={event => {
              setLoginInfo({...loginInfo, email: event.target.value})
            }}
            onKeyUp={enterLogin}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="password"
            label="비밀번호"
            type="password"
            id="password"
            autoComplete="current-password"
            onChange={event => {
              setLoginInfo({...loginInfo, password: event.target.value})
            }}
            onKeyUp={enterLogin}
          />
          {/*<FormControlLabel*/}
          {/*  control={<Checkbox value="remember" color="primary"/>}*/}
          {/*  label="Remember me"*/}
          {/*/>*/}
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={login}
          >
            로그인
          </Button>
        </div>
      </div>
      <Box mt={8}>
        <Copyright/>
      </Box>
    </Container>
  );
}

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%',
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default LoginPage;
