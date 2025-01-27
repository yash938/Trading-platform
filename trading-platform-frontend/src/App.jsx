import { useState } from 'react'
import './App.css'
import { Button } from './components/ui/button'
import Navbar from './page/Navbar/Navbar'
import Home from './Home/Home'
import { Route, Routes } from 'react-router-dom'
import Portfolio from './page/Portfolio'
import Acivity from './page/Activity/Acivity'
import Wallet from './Wallet/Wallet'
import WithDrawl from './page/WithDrawl'

import StockDetails from './page/StockDetails/StockDetails'
import WatchList from './page/WatcList/WatchList'
import Profile from './page/Profile/Profile'
import SearchCoin from './page/SearchCoin/SearchCoin'
import NotFound from './page/Notfound/NotFound'
import WithDrawlAdmin from './page/Admin/WithDrawlAdmin'
import PaymentDetails from './PaymentDetails/PaymentDetails'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Navbar/>
      <Routes>
        <Route path='/' element={<Home/>}/>
        <Route path='/portfolio' element={<Portfolio/>}/>
        <Route path='/activity' element={<Acivity/>}/>
        <Route path='/wallet' element={<Wallet/>}/>
        <Route path='/market/:id' element={<StockDetails/>}/>
        <Route path='/watchlist' element={<WatchList/>}/>
        <Route path='/profile' element={<Profile/>}/>
        <Route path='/withdrawal' element={<WithDrawl/>}/>
        <Route path='/search' element={<SearchCoin/>}/>
        <Route path='*' element={<NotFound/>}/>
        <Route path='/withdrawladmin' element={<WithDrawlAdmin/>}/>
        <Route path='/paymentDetail' element={<PaymentDetails/>}/>
      </Routes>
      
    </>
  )
}

export default App
