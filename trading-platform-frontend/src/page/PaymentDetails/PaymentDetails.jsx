import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import React from 'react';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { Button } from '@/components/ui/button';
import PAymentDetailsForm from './PAymentDetailsForm';


const PaymentDetails = () => {
  return (
    <div className='px-20'>
      <h1 className='text-3xl font-bold py-10'>Payment Details</h1>
      {true? <Card>
        <CardHeader>
          <CardTitle>Yes Bank</CardTitle>
          <CardDescription>************1223</CardDescription>
        </CardHeader>
        <CardContent>
          <div className='flex items-center mb-4'>
            <p className='w-32 font-semibold'>A/C Holder</p>
            <p className='text-gray-400'>: Yash Sharma</p>
          </div>
          <div className='flex items-center'>
            <p className='w-32 font-semibold'>IFSC</p>
            <p className='text-gray-400'>: YESB0000003</p>
          </div>
        </CardContent>
      </Card>: <Dialog>
  <DialogTrigger>
    <Button className='py-6'>Add PAyment Details</Button>
  </DialogTrigger>
  <DialogContent>
    <DialogHeader>
      <DialogTitle>Payment Details</DialogTitle>
      
    </DialogHeader>
    <PAymentDetailsForm/>
  </DialogContent>
</Dialog>}
     

    </div>
  );
};

export default PaymentDetails;
