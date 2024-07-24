import { useState, MouseEvent } from 'react';
import { IconButton, useMediaQuery, useTheme, Menu, Table, TableBody, TableRow, TableCell } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { normalizeString } from '../../utils/stringUtils'
import { useAllCountries } from '../../hooks/countryHook';

const Countries = () => {
  const navigate = useNavigate();
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm')); 
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [showCategory, setShowCategory] = useState<boolean>(false);

  const { countries } = useAllCountries();
  const capitalizedCountries = countries;

  const handleCategory = (countryName: string) => {
    navigate(`/quoc-gia/${normalizeString(countryName)}`);
    setShowCategory(false);
    setAnchorEl(null);
  };

  const handleCategoryClick = (event: MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
    setShowCategory(true);
  };

  const handleCategoryClose = () => {
    setAnchorEl(null);
    setShowCategory(false);
  };

  const handleCategoryMobileClick = () => {
    setAnchorEl(null);
    setShowCategory(!showCategory);
  };

  return (
    <div>
      {isMobile ? (
        <div>
          <IconButton
            size="small"
            edge="start"
            color="inherit"
            aria-label="menu"
            aria-controls="country-menu"
            aria-haspopup="true"
            onClick={handleCategoryMobileClick}
          >
            <span style={{ marginRight: '5px', marginLeft: '5px' }}>Quốc gia</span>
          </IconButton>
          {showCategory && (
            <div id="country-menu" style={{ visibility: 'visible'}}>
              <Table>
                <TableBody>
                  {capitalizedCountries?.map((cat, index) => (
                    index % 4 === 0 && (
                      <TableRow key={index}>
                        <TableCell style={{color: 'white', cursor: 'pointer'}} onClick={() => handleCategory(cat)}>{cat}</TableCell>
                        {capitalizedCountries[index + 1] && <TableCell style={{color: 'white', cursor: 'pointer'}} onClick={() => handleCategory(capitalizedCountries[index + 1])}>{capitalizedCountries[index + 1]}</TableCell>}
                        {capitalizedCountries[index + 2] && <TableCell style={{color: 'white', cursor: 'pointer'}} onClick={() => handleCategory(capitalizedCountries[index + 2])}>{capitalizedCountries[index + 2]}</TableCell>}
                        {capitalizedCountries[index + 3] && <TableCell style={{color: 'white', cursor: 'pointer'}} onClick={() => handleCategory(capitalizedCountries[index + 3])}>{capitalizedCountries[index + 3]}</TableCell>}
                      </TableRow>
                    )
                  ))}
                </TableBody>
              </Table>
            </div>
          )}
        </div>
      ) : (
        <div>
          <IconButton
            size="small"
            edge="start"
            color="inherit"
            aria-label="menu"
            aria-controls="country-menu"
            aria-haspopup="true"
            onClick={handleCategoryClick}
          >
            <span style={{ marginRight: '5px', marginLeft: '5px' }}>Quốc gia</span>
            
          </IconButton>
          <Menu
            id="country-menu"
            anchorEl={anchorEl}
            open={Boolean(anchorEl)}
            onClose={handleCategoryClose}
            slotProps={{
              paper: {
                style: {
                  background: theme.palette.background.default
                }
              }
            }}
          >
            <Table>
              <TableBody>
                {capitalizedCountries?.map((cat, index) => (
                  index % 4 === 0 && (
                    <TableRow key={index}>
                      <TableCell style={{ cursor: 'pointer'}} onClick={() => handleCategory(cat)}>{cat}</TableCell>
                      {capitalizedCountries[index + 1] && <TableCell style={{ cursor: 'pointer'}} onClick={() => handleCategory(capitalizedCountries[index + 1])}>{capitalizedCountries[index + 1]}</TableCell>}
                      {capitalizedCountries[index + 2] && <TableCell style={{ cursor: 'pointer'}} onClick={() => handleCategory(capitalizedCountries[index + 2])}>{capitalizedCountries[index + 2]}</TableCell>}
                      {capitalizedCountries[index + 3] && <TableCell style={{ cursor: 'pointer'}} onClick={() => handleCategory(capitalizedCountries[index + 3])}>{capitalizedCountries[index + 3]}</TableCell>}
                    </TableRow>
                  )
                ))}
              </TableBody>
            </Table>
          </Menu>
        </div>
      )}
    </div>
  );
};

export default Countries;