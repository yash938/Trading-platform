import { Card, CardHeader, CardTitle } from "@/components/ui/card";
import { CopyIcon, WalletIcon } from "lucide-react";
import React from "react";

const Wallet = () => {
  return (
    <div className="flex flex-col items-center">
      <div className="pt-10 w-full lg:w-[60%]">
        <Card>
          <CardHeader className="pb-9">
            <div className="flex justify-between items-center">
              <div className="flex items-center gap-5">
                <WalletIcon size={30} />
                <div>
                  <CardTitle className="text-2xl">My Wallet</CardTitle>
                  <div className="flex items-center gap-2">
                    <p className="text-gray-200 text-sm">#A475Ed</p>
                    <CopyIcon className="cursor-pointer hover:text-slate-300" />
                  </div>
                </div>
              </div>
            </div>
          </CardHeader>
        </Card>
      </div>
    </div>
  );
};

export default Wallet;
