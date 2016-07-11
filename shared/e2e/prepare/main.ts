import mockMessagingModule from '../mocks/messaging';

function prepare() {
    console.log('prepare');
    browser.addMockModule('e2e.mocks.messaging', mockMessagingModule);
}

export default prepare;