import React from 'react'
import {useTable} from 'react-table'

import amvhimg from '../../../amvhimg.png';

function Table({ columns, data }) {
	// Use the state and functions returned from useTable to build your UI
	const {
	  getTableProps,
	  getTableBodyProps,
	  headerGroups,
	  rows,
	  prepareRow,
	} = useTable({
	  columns,
	  data,
	})
  
	// Render the UI for your table
	return (
	  <table {...getTableProps()}>
		<thead>
		  {headerGroups.map(headerGroup => (
			<tr {...headerGroup.getHeaderGroupProps()}>
			  {headerGroup.headers.map(column => (
				<th {...column.getHeaderProps()}>{column.render('Header')}</th>
			  ))}
			</tr>
		  ))}
		</thead>
		<tbody {...getTableBodyProps()}>
		  {rows.map((row, i) => {
			prepareRow(row)
			return (
			  <tr {...row.getRowProps()}>
				{row.cells.map(cell => {
				  return <td {...cell.getCellProps()}>
					  {
						cell.column.Header === '  ' 
						? <button className="btn btn-light" >{cell.render('Cell')}대</button>     
						: cell.column.Header === '미운행' 
						  ?  <button className="btn btn-secondary" >{cell.render('Cell')}대</button>
						  : cell.column.Header === '운행' 
							? <button className="btn btn-success" >{cell.render('Cell')}대</button>
							: cell.column.Header === '정상' 
							  ? <button className="btn btn-primary" >{cell.render('Cell')}</button> 
							  : cell.column.Header === '이상치 경고' 
								? <button className="btn btn-danger" >{cell.render('Cell')}</button>
								: cell.column.Header === '고장 경고' 
								  ? <button className="btn btn-danger" >{cell.render('Cell')}</button>
								  // : cell.row.id === "0"
								  //   ? <p>{cell.render('Cell')}</p>
									: cell.column.Header === ' '
									  ? <div><img src={amvhimg} alt="amvh"></img><p>{cell.render('Cell')}</p></div>
									  : <p>{cell.render('Cell')}</p>
					  }
					</td>
				})}
			  </tr>
			)
		  })}
		</tbody>
	  </table>
	)
  }
  
  export default Table
  