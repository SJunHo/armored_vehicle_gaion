import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ComponentStory, ComponentMeta } from '@storybook/react';
import { DataLookUpNew } from '../DataLookUp/DataLookUpComponent/DataLookupNew';
import '../i18n';

export default {
  title: 'Component/DataNew',
  component: DataLookUpNew,
} as ComponentMeta<typeof DataLookUpNew>;

const Template: ComponentStory<typeof DataLookUpNew> = (args) => (
  <DataLookUpNew {...args} />
);

export const HundredPages = Template.bind({});
