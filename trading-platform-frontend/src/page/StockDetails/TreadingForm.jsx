import { Avatar, AvatarImage } from '@/components/ui/avatar';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { DotIcon } from '@radix-ui/react-icons';
import React, { useState } from 'react';

const TreadingForm = () => {
  const [orderType, setOrderType] = useState("BUY");

  const handleChange = (event) => {
    // Handle input change logic here if needed
    console.log("Amount entered:", event.target.value);
  };

  return (
    <div className="space-y-10 p-5">
      {/* Amount Input Section */}
      <div>
        <div className="flex gap-4 items-center justify-between">
          <Input
            className="py-7 focus:outline-none"
            placeholder="Enter amount..."
            onChange={handleChange}
            type="number"
            name="amount"
          />
          <div>
            <p className="border text-2xl flex justify-center items-center w-36 h-14 rounded-md">
              4563
            </p>
          </div>
        </div>
        {/* Insufficient Balance Warning */}
        {false && (
          <h1 className="text-red-600 text-center pt-4">
            Insufficient wallet balance to buy
          </h1>
        )}
      </div>

      {/* BTC Details Section */}
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

      {/* Order Type Section */}
      <div className="flex items-center justify-between">
        <p>Order Type</p>
        <p>Market Order</p>
      </div>

      {/* Available Balance/Quantity Section */}
      <div className="flex items-center justify-between">
        <p>
          {orderType === "BUY"
            ? "Available cash"
            : "Available quantity"}
        </p>
        <p>
        {orderType === "BUY"
            ? 9000
            : 23.08}
        </p>
      </div>

      {/* Buttons Section */}
      <div>
        {/* Main Action Button */}
        <Button
          className={`w-full py-6 ${
            orderType === "SELL" ? "bg-red-600 text-white" : ""
          }`}
        >
          {orderType}
        </Button>

        {/* Toggle Order Type Button */}
        <Button
          onClick={() => setOrderType(orderType === "BUY" ? "SELL" : "BUY")}
          className="mt-4 w-full"
        >
          {orderType === "BUY" ? "Or Sell" : "Or Buy"}
        </Button>
      </div>
    </div>
  );
};

export default TreadingForm;
