import React, {useState} from 'react';
import clsx from 'clsx';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import EcoIcon from '@material-ui/icons/Eco';
import {HashRouter, Route, Switch} from "react-router-dom";
import {AppBar, Box, Container, Divider, Drawer, IconButton, makeStyles, Toolbar, Typography} from "@material-ui/core";
import MainMenus from "./components/menus/Menus";
import Copyright from "./components/copyright/Copyright";
import DocumentCreatePage from "./components/documents/create";
import DocumentOutboxPage from "./components/documents/outbox";
import DocumentInboxPage from "./components/documents/inbox";
import DocumentArchivePage from "./components/documents/archive";
import VacationCreatePage from "./components/vacations/create";
import LoginPage from "./components/login/LoginPage";
import {green} from "@material-ui/core/colors";
import MainPage from "./components/main/MainPage";
import {IUserLoginInfoReducer, resetUserLoginInfo} from "./reducers/UserLoginInfoReducer";
import {ReducerType} from "./reducers/RootReducer";
import {useDispatch, useSelector} from "react-redux";

const Router = () => {

  const classes = useStyles();

  const dispatch = useDispatch();

  const [open, setOpen] = useState(false);
  const userInfo = useSelector<ReducerType, IUserLoginInfoReducer>(state => state.userLoginInfoReducer);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const onLogout = () => {
    localStorage.removeItem("token");
    dispatch(resetUserLoginInfo());
  }

  return (
    <HashRouter>
      <Switch>
        <Route path="/" exact component={LoginPage}/>

        <>
          <div className={classes.root}>
            <AppBar position="fixed" className={clsx(classes.appBar, open && classes.appBarShift)}>
              <Toolbar className={classes.toolbar}>
                <IconButton
                  edge="start"
                  color="inherit"
                  aria-label="open drawer"
                  onClick={handleDrawerOpen}
                  className={clsx(classes.menuButton, open && classes.menuButtonHidden)}
                >
                  <MenuIcon/>
                </IconButton>
                <Typography component="h1" variant="h6" color="inherit" noWrap className={classes.title}>
                  WBD 인사시스템
                </Typography>
                <span className={classes.userInfo}>
            <EcoIcon className={classes.userIcon}/>
            <span className={classes.userGreeting}>
              {userInfo.name} 님 환영합니다! ( {userInfo.email} )
            </span>
            <div className={classes.logout}>
              <ExitToAppIcon className={classes.logoutIcon}/>
              <a href="/#" className={classes.logoutButton} onClick={onLogout}>
                <span className={classes.logoutButtonText}>
                  로그아웃
                </span>
              </a>
            </div>
          </span>
              </Toolbar>
            </AppBar>
            <Drawer
              variant="permanent"
              classes={{
                paper: clsx(classes.drawerPaper, !open && classes.drawerPaperClose),
              }}
              open={open}
            >
              <div className={classes.toolbarIcon}>
                <IconButton onClick={handleDrawerClose}>
                  <ChevronLeftIcon/>
                </IconButton>
              </div>
              <Divider/>
              <MainMenus/>
            </Drawer>
            <main className={classes.content}>
              <div className={classes.appBarSpacer}/>
              <Container maxWidth="lg" className={classes.container}>

                <Switch>
                  <Route path="/main" exact component={MainPage}/>
                  <Route path="/documents/create" exact component={DocumentCreatePage}/>
                  <Route path="/documents/outbox" exact component={DocumentOutboxPage}/>
                  <Route path="/documents/inbox" exact component={DocumentInboxPage}/>
                  <Route path="/documents/archive" exact component={DocumentArchivePage}/>
                  <Route path="/vacations/create" exact component={VacationCreatePage}/>
                  <Route component={() => <><h2>잘못된 경로입니다. 뒤로 가기를 눌러주세요.</h2></>}/>
                </Switch>

                <Box pt={4}>
                  <Copyright/>
                </Box>

              </Container>
            </main>
          </div>
        </>
      </Switch>
    </HashRouter>
  );
}

const drawerWidth = 280;

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
  },
  toolbar: {
    paddingRight: 24,
  },
  toolbarIcon: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: '0 8px',
    ...theme.mixins.toolbar,
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  menuButtonHidden: {
    display: 'none',
  },
  title: {
    flexGrow: 1,
  },
  drawerPaper: {
    position: 'relative',
    whiteSpace: 'nowrap',
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerPaperClose: {
    overflowX: 'hidden',
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    width: theme.spacing(7),
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing(9),
    },
  },
  appBarSpacer: theme.mixins.toolbar,
  content: {
    flexGrow: 1,
    height: '100vh',
    overflow: 'auto',
  },
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(2),
    display: 'flex',
    overflow: 'auto',
    flexDirection: 'column',
  },
  fixedHeight: {
    height: 240,
  },
  userInfo: {
    marginLeft: 'auto',
    display: 'inline-flex',
  },
  userIcon: {
    color: green[500],
    width: '20px'
  },
  userGreeting: {
    marginTop: '2px',
  },
  logout: {
    paddingLeft: 8,
  },
  logoutIcon: {
    width: 15,
    float: 'left'
  },
  logoutButton: {
    paddingLeft: 1,
    verticalAlign: 'text-top',
    '&:visited': {
      color: 'black'
    },
    cursor: 'pointer',
  },
  logoutButtonText: {
    color: 'white',
    textDecoration: 'underline',
    textDecorationColor: 'white'
  },
}));

export default Router;
