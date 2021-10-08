import React from 'react';
import Router from "./Router";
import './import'
import {createTheme, MuiThemeProvider} from "@material-ui/core";
import CssBaseline from "@material-ui/core/CssBaseline";
import {persistedStore} from "./stores/Store";
import {PersistGate} from "redux-persist/integration/react";
import {Provider} from "react-redux";

const {store, persistor} = persistedStore;

const theme = createTheme({
  typography: {
    fontFamily: 'ARIAL'
  }
});

function App() {
  return (
    <>
      <MuiThemeProvider theme={theme}>
        <CssBaseline/>
        <Provider store={store}>
          <PersistGate loading={null} persistor={persistor}>
            <Router/>
          </PersistGate>
        </Provider>
      </MuiThemeProvider>
    </>
  );
}

export default App;
