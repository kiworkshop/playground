import {combineReducers} from "@reduxjs/toolkit";
import {userLoginInfoReducer} from "./UserLoginInfoReducer";

const rootReducer = combineReducers({
  userLoginInfoReducer,
});

export default rootReducer;
export type ReducerType = ReturnType<typeof rootReducer>;
