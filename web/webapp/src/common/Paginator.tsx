import React, { useEffect, useState } from "react";
import Pagination from "react-bootstrap/Pagination";

type PaginatorType = {
  pageCount: number;
  size?: number;
  selectedPage?: number;
  onChange?: (index: number) => any;
};

export const Paginator: React.FC<PaginatorType> = ({
  pageCount,
  onChange,
  selectedPage = 0,
}) => {
  const numberOfPage = pageCount;
  const [internalSelectedPage, setInternalSelectedPage] =
    useState(selectedPage);

  useEffect(() => {
    setInternalSelectedPage(selectedPage);
  }, [selectedPage]);

  const handleChangePage = (index: number) => {
    onChange ? onChange(index) : setInternalSelectedPage(index);
  };

  const firstBlockCount =
    numberOfPage < 10
      ? numberOfPage
      : selectedPage < 5 && selectedPage >= 2
      ? 6
      : 3;
  const lastBlockCount =
    numberOfPage < 10
      ? 0
      : selectedPage < numberOfPage - 2 && selectedPage > numberOfPage - 6
      ? 6
      : 3;

  return (
    <Pagination>
      <Pagination.First
        onClick={() => {
          if (internalSelectedPage > 0) {
            handleChangePage(internalSelectedPage - 1);
          }
        }}
      />
      {new Array(firstBlockCount).fill(0).map((_, index) => (
        <Pagination.Item
          key={index + 1}
          onClick={() => {
            handleChangePage(index);
          }}
        >
          {index + 1}
        </Pagination.Item>
      ))}
      {numberOfPage > 10 &&
        selectedPage > 4 &&
        selectedPage < numberOfPage - 5 && (
          <>
            <Pagination.Ellipsis />
            {new Array(3).fill(0).map((_, index) => (
              <Pagination.Item
                key={index + selectedPage - 1}
                onClick={() => {
                  handleChangePage(index);
                }}
              >
                {index + selectedPage - 1}
              </Pagination.Item>
            ))}
          </>
        )}
      {numberOfPage > 10 && (
        <>
          <Pagination.Ellipsis />
          {new Array(lastBlockCount).fill(0).map((_, index) => (
            <Pagination.Item
              key={numberOfPage - lastBlockCount + index}
              onClick={() => {
                handleChangePage(index);
              }}
            >
              {numberOfPage - lastBlockCount + index}
            </Pagination.Item>
          ))}
        </>
      )}
      <Pagination.Last
        onClick={() => {
          if (internalSelectedPage < numberOfPage) {
            handleChangePage(internalSelectedPage + 1);
          }
        }}
      />
    </Pagination>
  );
};
