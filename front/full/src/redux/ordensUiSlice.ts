import { createSlice } from "@reduxjs/toolkit";

export type StatusFiltro = "TODAS" | "ABERTA" | "EM_EXECUCAO" | "FINALIZADA";

interface OrdensUiState {
  filtroStatus: StatusFiltro;
}

const initialState: OrdensUiState = {
  filtroStatus: "TODAS",
};

const ordensUiSlice = createSlice({
  name: "ordensUi",
  initialState,
  reducers: {
    setFiltroStatus(state, action: { payload: StatusFiltro }) {
      state.filtroStatus = action.payload;
    },
  },
});

export const { setFiltroStatus } = ordensUiSlice.actions;
export default ordensUiSlice.reducer;