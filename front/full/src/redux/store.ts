import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./authSlice";
import ordensUiReducer from "./ordensUiSlice";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    ordensUi: ordensUiReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;