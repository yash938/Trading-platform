import React from 'react'
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Avatar, AvatarImage } from '@/components/ui/avatar'

const Portfolio = () => {
  return (
    <div className='p-5 lg:px-20'>
      <h1 className='font-bold text-3xl pb-5'>Portfolio</h1>
       <Table>
        <TableHeader>
          <TableRow>
            <TableHead className="">Asset</TableHead>
            <TableHead>Pice</TableHead>
            <TableHead>Unit</TableHead>
            <TableHead>Change</TableHead>
            <TableHead>Change%</TableHead>
            <TableHead>Volume</TableHead>
           
          </TableRow>
        </TableHeader>
        <TableBody>
          {[1,1,1,1,1,1,1,1,1,1,1,1,1].map((item,index)=>
           <TableRow key={index}>
           <TableCell className="font-medium flex items-center gap-2"><Avatar className='-z-50'>
             <AvatarImage src="https://cdn.pixabay.com/photo/2021/05/09/13/10/finance-6240949_960_720.png"/>
             </Avatar>
             <span>Bitcoin</span>
             </TableCell>
           <TableCell>BTC</TableCell>
           <TableCell>9121234567 </TableCell>
           <TableCell>1321345678651 </TableCell>
           <TableCell>-0.20009</TableCell> 
           <TableCell className="text-right">$69249</TableCell>
         </TableRow>
          )}
          <TableRow>
            <TableCell className="font-medium flex items-center gap-2"><Avatar className='-z-50'>
              <AvatarImage src="https://cdn.pixabay.com/photo/2021/05/09/13/10/finance-6240949_960_720.png"/>
              </Avatar>
              <span>Bitcoin</span>
              </TableCell>
            <TableCell>BTC</TableCell>
            <TableCell>9121234567 </TableCell>
            <TableCell>1321345678651 </TableCell>
            <TableCell>-0.20009</TableCell> 
            <TableCell className="text-right">$69249</TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </div>
  )
}

export default Portfolio
