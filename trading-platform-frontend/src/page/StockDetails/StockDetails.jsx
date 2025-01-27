import { Avatar, AvatarImage } from '@/components/ui/avatar';
import { Button } from '@/components/ui/button';
import { BookmarkFilledIcon, BookmarkIcon, DotIcon } from '@radix-ui/react-icons';
import React from 'react';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import TreadingForm from './TreadingForm';
import StockChart from '../Home/StockChart';

const StockDetails = () => {
  return (
    <div className="p-5 mt-5">
      {/* Header Section */}
      <div className="flex justify-between items-center mb-4">
        <div className="flex gap-5 items-center">
          <p className="text-lg font-semibold">Stock Details</p>
        </div>
      </div>
     

      {/* Stock Information */}
      <div className="flex justify-between items-center">
        {/* Left Section: BTC Details */}
        <div className="flex items-center gap-2">
          <Avatar className="h-6 w-6">
            <AvatarImage
              src="https://cdn.pixabay.com/photo/2021/05/09/13/10/finance-6240949_960_720.png"
              alt="BTC"
            />
          </Avatar>
          <p className="font-medium">BTC</p>
          <DotIcon className="text-gray-400" />
          <p className="text-gray-600">Bitcoin</p>
        </div>

        {/* Right Section: Bookmark Icon and Tread Button */}
        <div className="flex items-center gap-3">
          {/* Bookmark Icon */}
          <Button className="flex items-center">
            {true ? (
              <BookmarkFilledIcon className="h-6 w-6 text-blue-500" />
            ) : (
              <BookmarkIcon className="h-6 w-6 text-gray-500" />
            )}
          </Button>

          {/* Tread Dialog */}
          <Dialog>
            <DialogTrigger>
              <Button size="lg">Tread</Button>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>How much do you want to spend?</DialogTitle>
              </DialogHeader>
              <TreadingForm/>
            </DialogContent>
          </Dialog>
        </div>
      </div>
     

      {/* Stock Price and Change */}
      <div className="flex items-end gap-2 mt-2">
        <p className="text-xl font-bold text-black">$6,554</p>
        <p className="text-red-600">
          <span>-1,321,233,444.567</span>
          <span className="ml-1">(-0.23232%)</span>
        </p>
      </div>
      <div className='mt-14'>
      <StockChart/>
      </div>
    </div>
  );
};

export default StockDetails;
