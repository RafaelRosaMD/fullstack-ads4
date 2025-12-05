import React from "react";
import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";
import type { RootState } from "../redux/store";

interface Props {
  children: React.ReactNode;
}

export default function PrivateRoute({ children }: Props) {
  const isAutenticado = useSelector(
    (state: RootState) => state.auth.isAutenticado
  );

  if (!isAutenticado) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
}