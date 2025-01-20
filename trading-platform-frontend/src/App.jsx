import { useState } from 'react'
import './App.css'
import { Button } from './components/ui/button'
import Navbar from './page/Navbar/Navbar'
import Home from './Home/Home'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Navbar/>
      <Home/>
    </>
  )
}

export default App
