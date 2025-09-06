import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import AppRoutes from './route'
import Footer from './components/footer'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
  
    <AppRoutes/>
  </StrictMode>,
)
