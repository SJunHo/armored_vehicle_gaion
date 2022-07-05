import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ComponentStory, ComponentMeta } from '@storybook/react';
import { DataLookUpDelete } from '../DataLookUp/DataLookUpComponent/DataLookUpDelete';
import '../i18n';

export default {
  title: 'Component/DLUDelete',
  component: DataLookUpDelete,
} as ComponentMeta<typeof DataLookUpDelete>;

const Template: ComponentStory<typeof DataLookUpDelete> = (args) => (
  <DataLookUpDelete {...args} />
);

export const HundredPages = Template.bind({});
