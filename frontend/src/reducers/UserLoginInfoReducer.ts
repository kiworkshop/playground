import {createSlice, PayloadAction} from "@reduxjs/toolkit";

export interface IUserLoginInfoReducer {
  email: string
  name: string
}

const initialLoginInfo = {
  email: '',
  name: ''
};

const userLoginInfoSlice = createSlice({
  name: "userLoginInfo",
  initialState: initialLoginInfo as IUserLoginInfoReducer,
  reducers: {
    setUserLoginInfo: (state, action: PayloadAction<IUserLoginInfoReducer>) => {
      state.email = action.payload.email;
      state.name = action.payload.name;
    },
    resetUserLoginInfo: (state) => {
      state.email = initialLoginInfo.email;
      state.name = initialLoginInfo.name;
    },
  }
});

export const {setUserLoginInfo, resetUserLoginInfo} = userLoginInfoSlice.actions;
export const userLoginInfoReducer = userLoginInfoSlice.reducer;
