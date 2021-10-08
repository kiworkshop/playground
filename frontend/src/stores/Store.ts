import {configureStore, getDefaultMiddleware} from "@reduxjs/toolkit";
import {persistReducer, persistStore} from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import {FLUSH, PAUSE, PERSIST, PURGE, REGISTER, REHYDRATE} from "redux-persist/es/constants";
import rootReducer from "../reducers/RootReducer";

const persistConfig = {
  key: 'root',
  blacklist: [ // persist 기능을 사용하지 않을 Reducer
  ],
  storage
};

const enhancedReducer = persistReducer(persistConfig, rootReducer);

/**
 * redux-persist 를 사용하지 않으면 새로고침 시 저장된 Store 정보가 날아간다.
 * redux-toolkit에서 redux-persist를 사용하기 위해 몇 가지 Action Type을 ignore 해야 한다.
 */
export const store = configureStore({
  reducer: enhancedReducer,
  middleware: getDefaultMiddleware({
    serializableCheck: {
      ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
    },
  }),
});

const persistor = persistStore(store);

export const persistedStore = {store, persistor};
