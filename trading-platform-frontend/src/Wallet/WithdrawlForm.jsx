import { Button } from '@/components/ui/button';
import { DialogClose } from '@/components/ui/dialog';
import { Input } from '@/components/ui/input'
import React from 'react'

const WithdrawlForm = () => {
     const [amount, setAmount] = React.useState("");


      const handleChange = (e) => {
        setAmount(e.target.value);
      };
  

      const handleSubmit=(e)=>{
        console.log(amount)
      }

  return (
    <div className='pt-10 space-y-5'>
      <div className='flex justify-between items-center rounded-md bg-slate-900 text-xl font-bold px-5 py-4'>
        <p>Available balance</p>
        <p>$9000</p>
      </div>
      <div className='flex flex-col items-center'>
            <h1>Enter Withdrawl amount</h1>
            <div className='flex items-center'>
                <Input onChange={handleChange} value={amount} className='withdrawlInput py-7 border-none outline-none focus:outline-none px-0 text=wxl text-center' placeholder='$999' type="number" />
            </div>
      </div>
      <div>
        <p className='pb-2'>
        transfer to
        </p>
        <div className='flex items-center gap-5 border px-5 py-2 rounded-md '>
            <img 
             className='h-8 w-8' src="https://cdn.iconscout.com/icon/premium/png-512-thumb/bank-71-85594.png?f=webp&w=256" alt="" />
            <div>
                <p className='text-xl font-bold '>Yes Bank</p>
                <p className='text-xs'>**********1234</p>
            </div>
        </div>
      </div>
      <DialogClose className='w-full'>
      <Button onClick={handleSubmit} className='w-full py-7 text-xl'>WithDraw</Button>
      </DialogClose>
     
    </div>
  )
}

export default WithdrawlForm
