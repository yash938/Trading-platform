import React from "react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Avatar, AvatarImage } from "@/components/ui/avatar";

const WithDrawl = () => {
  return (
    <div className="p-5 lg:px-20">
      <h1 className="font-bold text-3xl pb-5">Withdrawl</h1>
      <Table className="border">
        <TableHeader>
          <TableRow>
            <TableHead className="py-5">Date</TableHead>
            <TableHead>Method</TableHead>
            <TableHead>Amount</TableHead>

            <TableHead className="text-right">Status</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1].map((item, index) => (
            <TableRow key={index}>
              <TableCell>
                <p>20/05/2005</p>
                <p className="text-gray-400">12:02:20</p>
              </TableCell>
              <TableCell className="font-medium flex items-center gap-2">
                <Avatar className="-z-50">
                  <AvatarImage src="https://cdn.pixabay.com/photo/2021/05/09/13/10/finance-6240949_960_720.png" />
                </Avatar>
                <span>Bitcoin</span>
              </TableCell>

              <TableCell className="text-right">$69249</TableCell>
              <TableCell className="text-right">345</TableCell>
            </TableRow>
          ))}
          <TableRow>
            <TableCell>
              <p>June 2, 2025 at 11:43</p>
            </TableCell>

            <TableCell>$67676</TableCell>
            <TableCell className="text-right">$69249</TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </div>
  );
};

export default WithDrawl;
