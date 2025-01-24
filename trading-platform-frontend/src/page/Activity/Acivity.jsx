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
import { Button } from '@/components/ui/button'
import { BookmarkFilledIcon } from '@radix-ui/react-icons'

const Acivity = () => {
  return (
    <div className='p-5 lg:px-20'>
            <h1 className='font-bold text-3xl pb-5'>Activity</h1>
               <Table className='border'>
               <TableHeader>
                 <TableRow>
                   <TableHead className="py-5">Date 
                     & Time
                   </TableHead>
                   <TableHead>Treading Pair</TableHead>
                   <TableHead>BuyPrice</TableHead>
                   <TableHead>SellPrice</TableHead>
                   <TableHead>Order Type</TableHead>
                   <TableHead className="text-right">Profit & Loss</TableHead>
                   <TableHead className="text-right">Value</TableHead>
                 </TableRow>
               </TableHeader>
               <TableBody>
                 {[1,1,1,1,1,1,1,1,1,1,1,1,1].map((item,index)=>
                  <TableRow key={index}>
                    <TableCell><p>20/05/2005</p>
                    <p className='text-gray-400'>12:02:20</p>
                    </TableCell>
                  <TableCell className="font-medium flex items-center gap-2"><Avatar className='-z-50'>
                    <AvatarImage src="https://cdn.pixabay.com/photo/2021/05/09/13/10/finance-6240949_960_720.png"/>
                    </Avatar>
                    <span>Bitcoin</span>
                    </TableCell>
                  
                  <TableCell>9121234567 </TableCell>
                  <TableCell>1321345678651 </TableCell>
                  <TableCell>-0.20009</TableCell> 
                  <TableCell className="text-right">$69249</TableCell>
                  <TableCell className="text-right">
                   345
                  </TableCell>
                </TableRow>
                 )}
                 <TableRow>
                 <TableCell>
                  <p>
                    2024/05/31
                  </p>
                  <p>
                    12:02:23
                  </p>
                 </TableCell>
                   <TableCell className="font-medium flex items-center gap-2"><Avatar className='-z-50'>
                     <AvatarImage src="https://cdn.pixabay.com/photo/2021/05/09/13/10/finance-6240949_960_720.png"/>
                     </Avatar>
                     <span>Bitcoin</span>
                     </TableCell>
                   <TableCell>$67676</TableCell>
              
                   <TableCell>1321345678651 </TableCell>
                   <TableCell>-0.20009</TableCell> 
                   <TableCell className="text-right">$69249</TableCell>
                 </TableRow>
               </TableBody>
             </Table>
          </div>
  )
}

export default Acivity
